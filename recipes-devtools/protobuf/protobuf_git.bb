SUMMARY = "Google Protocol Buffers - structured data serialization mechanism"
HOMEPAGE = "http://code.google.com/p/protobuf/"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=142112c649df70be3d7022cfb29dd827"

# Version 3.0.0-beta1
SRCREV = "23408684b4d2bf1b25e14314413a14d542c18bc4"
SRC_URI = "git://github.com/google/protobuf.git"

# Must change PR whenever the SRCREV is changed
PR = "r0"

# Build static protoc  with --disable-shared for nativesdk only
#EXTRA_OECONF += " --with-protoc=echo --disable-shared"
EXTRA_OECONF += " --with-protoc=echo"

inherit autotools setuptools

# ISCO: Not currently using Python protobufs
#LANG_SUPPORT="cpp python"
LANG_SUPPORT="cpp"
PYTHON_SRC_DIR="python"

S = "${WORKDIR}/git"

do_compile() {
	# Compile protoc compiler and shared libraries
	base_do_compile
}

do_install() {
	local olddir=`pwd`

	# Install compiler and C++ libraries
	autotools_do_install

	# Install Python libraries
	# ISCO: Not currently using Python protobufs
	#export PROTOC="${STAGING_BINDIR_NATIVE}/protoc"
	#cd "${S}/${PYTHON_SRC_DIR}"
	#distutils_do_install

	cd "$olddir"
}

do_install_append_class-target() {
	# Remove protoc compiler and its library. 
	rm -rf ${D}/${bindir}
	rm -f ${D}/${libdir}/libprotoc.so*

	# Remove standard non-lite library if not needed in future
	#rm -f ${D}/${libdir}/libprotobuf.so*
}

BBCLASSEXTEND = "native nativesdk"

