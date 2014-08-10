#!/bin/sh
#
# To change root FS before reboot, e.g.
# echo "/dev/mmcblk0p2" >/home/root/rootfsdev
#

PATH=/sbin:/bin:/usr/sbin:/usr/bin

ROOTPARTA=mmcblk0p2
ROOTPARTB=mmcblk0p4
DATAFSTYPE=ext4
DATAPART=mmcblk0p3
IMGSAVEDIR=/bootdata/images
FSERROR=0

check_fs()
{
    FSERROR=0
    e2fsck -p $ROOTDEV

    RET=$?
    if [ $RET -eq 2 ]; then
        # TODO: Should maintain a nonvolatile error count and give up if exceeded
        echo "INITRAMFS: Filesystem errors corrected, requires reboot"
        /sbin/reboot -n -f
    elif [ $RET -ge 3 ]; then
        echo "INITRAMFS: ERROR: $ROOTDEV FS check failed!"
        FSERROR=1
    fi
}

## Start of init script
mkdir -p proc
mkdir -p sys
mount -t proc proc /proc
mount -t sysfs sysfs /sys

# Remove the default fstab (should be done in a recipe)
rm /etc/fstab

# Start udev. Disable MMC automount
mkdir -p /var/run/udev
echo /dev/mmc* >>/etc/udev/mount.blacklist
/etc/init.d/udev start

# Check for valid root filesystem and pass control to it.
mkdir /bootdata
mount -t $DATAFSTYPE /dev/$DATAPART /bootdata

if [ -f $IMGSAVEDIR/rootfsdev ]; then
    ROOTDEV=`cat $IMGSAVEDIR/rootfsdev`  

    # Check validity of selection
    if [ "$ROOTDEV" != "/dev/$ROOTPARTA" ] && [ "$ROOTDEV" != "/dev/$ROOTPARTB" ]; then
        echo "INITRAMFS: ERROR: Invalid Root Partition Selected!"
    else
        echo "INITRAMFS: Root FS device $ROOTDEV was selected"

        # Perform intergrity check on selected root FS
        check_fs

        if [ $FSERROR -ne 0 ]; then
            # TODO: Try reverting to the other FS upon failure.
            # Only revert when an update was actually attempted?
            # A revert also requires that the kernel be reverted if
            # both were changed. Need to add logic for that.
            # For now, just exiting to shell.
            #if [ "$ROOTDEV" == "/dev/$ROOTPARTA" ]; then
            # ROOTDEV = /dev/$ROOTPARTB
            #else
            # ROOTDEV = /dev/$ROOTPARTA
            # fi
            #swap_rootdev
            #check_fs
            echo "FS check failed"
        fi

        # Mount the root filesystem.
        mkdir /newroot
        mount -t ext4 $ROOTDEV /newroot

        if [ $? -ne 0 ]; then
            echo "INITRAMFS: ERROR: Failed to mount $ROOTDEV!"
        else
            # Switch to the root FS
            echo "INITRAMFS: Switching root to $ROOTDEV ..."

            # Temporarily move everything else mounted to old root.
            mount --move /sys /newroot/sys
            mount --move /proc /newroot/proc
            mount --move /dev /newroot/dev

            # Now switch to the new filesystem and run /sbin/init from it.
            exec switch_root /newroot /sbin/init
        fi
    fi
else
    echo "INITRAMFS: ERROR: Root device selection not found!"
fi

echo "INITRAMFS: Start networking and recovery shell"

# Valid FS not found: Start network and recovery shell.
# Create interfaces file. (should be done in a recipe at build time)
echo "auto lo" > /etc/network/interfaces
echo "iface lo inet loopback" >> /etc/network/interfaces
echo "auto eth0" >> /etc/network/interfaces
echo "iface eth0 inet static" >> /etc/network/interfaces
echo "    address 10.0.1.2" >> /etc/network/interfaces                
echo "    netmask 255.255.255.0" >> /etc/network/interfaces                  
echo "    network 10.0.1.0" >> /etc/network/interfaces                    
echo "    gateway 10.0.1.1" >> /etc/network/interfaces 
hostname ramboot
/etc/init.d/networking start

# Start SSH server
/etc/init.d/dropbear start

# Run a shell 
echo "RAM Boot Maintenance and Recovery Shell" > /etc/issue
/sbin/getty 115200 ttyO0 -w

/etc/init.d/dropbear stop
/etc/init.d/networking stop
/etc/init.d/udev stop

echo "INITRAMFS: Exit $0: Rebooting ..."
sleep 2
/sbin/reboot -n -f

