#!/bin/sh
#
# /init script for initramfs
#

PATH=/sbin:/bin:/usr/sbin:/usr/bin

ROOTPARTA=mmcblk0p2
ROOTPARTB=mmcblk0p3
DATAFSTYPE=ext4
DATAPART=mmcblk0p4
#DATAFSTYPE=vfat
#DATAPART=mmcblk0p1
IMGSAVEDIR=/bootdata/images
TTYDEV=ttyO0
FSERROR=0
SELROOTDEV="none"

# Add -f to reboot since there's no sysV shutdown script
reboot_cmd="/sbin/reboot -f"

# Make system directory nodes
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

# Mount boot image flags directory
mkdir /bootdata
mount -t $DATAFSTYPE /dev/$DATAPART /bootdata

# Check for valid root filesystem and pass control to it.
if [ -f $IMGSAVEDIR/rootfsdev ]; then
    reqrootdev=`cat $IMGSAVEDIR/rootfsdev`

    # Check if selection is valid
    if [ "$reqrootdev" != "/dev/$ROOTPARTA" ] && [ "$reqrootdev" != "/dev/$ROOTPARTB" ]; then
        echo "INITRAMFS: ERROR: Invalid Root Partition Selected!"
    else
        echo "INITRAMFS: Root FS device $reqrootdev was selected"

        # Perform integrity check on selected root FS.
        # Returns 0 if good, 1 if errors were corrected, >1 if bad
        e2fsck -p $reqrootdev
        
        FSERROR=$?
        if [ $FSERROR -le 1 ]; then
            SELROOTDEV=$reqrootdev
        else
            echo "INITRAMFS: Filesystem check failed on $reqrootdev! (error=$FSERROR)"

            # Try reverting to the other partition
            if [ "$reqrootdev" == "/dev/$ROOTPARTA" ]; then
                ALTROOTDEV="/dev/$ROOTPARTB"
            else
                ALTROOTDEV="/dev/$ROOTPARTA"
            fi

            echo "INITRAMFS: Trying alternate root FS $ALTROOTDEV"
            e2fsck -p $ALTROOTDEV
            FSERROR=$?
            if [ $FSERROR -le 1 ]; then
                SELROOTDEV=$ALTROOTDEV
            else
                echo "INITRAMFS: Filesystem check failed on $ALTROOTDEV! (error=$FSERROR)"          
            fi
        fi

        if [ $FSERROR -le 1 ]; then
            # Mount the root filesystem.
            mkdir /newroot
            mount -t ext4 $SELROOTDEV /newroot

            if [ $? -ne 0 ]; then
                echo "INITRAMFS: ERROR: Failed to mount $SELROOTDEV!"
            else
                # Switch to the root FS
                echo "INITRAMFS: Switching root to $SELROOTDEV ..."

                umount /dev/$DATAPART 

                # Temporarily move everything else mounted to old root.
                mount --move /sys /newroot/sys
                mount --move /proc /newroot/proc
                mount --move /dev /newroot/dev

                # Now switch to the new filesystem and run /sbin/init from it.
                exec switch_root /newroot /sbin/init
            fi
        fi      
    fi
else
    echo "INITRAMFS: ERROR: Root device selection not found!"
fi

# Valid FS not found: Start recovery shell.
echo "INITRAMFS: Start recovery shell"

# Make a 'reboot' alias for the user
mkdir -p /usr/local/bin
echo $reboot_cmd > /usr/local/bin/reboot
chmod 755 /usr/local/bin/reboot

# Mount pseudo-terminal nodes
mkdir -p -m 755 /dev/pts
mount devpts /dev/pts -t devpts

# Start networking
hostname ramboot
/etc/init.d/networking start

# Start SSH server
/etc/init.d/dropbear start

# Run a shell 
echo "RAM Boot Recovery Shell" > /etc/issue
/sbin/getty 115200 $TTYDEV

/etc/init.d/dropbear stop
/etc/init.d/networking stop
/etc/init.d/udev stop

echo "INITRAMFS: Exit $0: Rebooting ..."
sleep 2
$reboot_cmd
