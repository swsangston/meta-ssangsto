
inherit extrausers

# User Configuration:
#   - Add admin user with default password "admin123". SFTP is
#     restricted to the logs directory. SSH and console logins are disabled.
#   - Add steve user with default password "iscoclarus".
#
# Encrypted passwords are from /etc/shadow after running this command on
# a target board:
#       echo admin:admin123 | chpasswd -cDES
# DES encryption must be used since other methods are machine-dependent.
EXTRA_USERS_PARAMS = "\
    useradd -d /var/log -s /sbin/nologin -p 'O2yhBCJFx2VoI' admin; \
    useradd -p 'DnO3l8kEsv9bo' iscoeng; \
    useradd -p 'DnO3l8kEsv9bo' steve; \
    " 
    
# Add root password ("isco123" example)
# Disabled for initial development.
#EXTRA_USERS_PARAMS += " \
#   usermod -p 'A9SgQj3kDf2dk' root; \
#   "
