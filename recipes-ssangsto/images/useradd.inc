inherit extrausers

# User Configuration:
# Add admin user with default password "admin123".
# Encrypted passwords were from /etc/shadow on target after running
# 'passwd' there.
EXTRA_USERS_PARAMS = "\
	useradd -p '4HzSgm8HWZesE' admin; \
"

# TODO:
# Add root password (Disabled for initial development)
# Set admin home directory to location of log files? 
#EXTRA_USERS_PARAMS += " \
#   usermod -p 'H2vVqwFYQeOUM' root; \
#   usermod -d /home/root/files admin; \
# "

