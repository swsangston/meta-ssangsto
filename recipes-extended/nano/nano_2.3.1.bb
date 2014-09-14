SUMMARY = "GNU nano - an enhanced clone of the Pico text editor"

DESCRIPTION = "GNU nano - an enhanced clone of the Pico text editor"

AUTHOR = "Chris Allegretta <chris@asty.org>, \
David Lawrence Ramsey <pooka109@gmail.com>, \
Jordi Mallach <jordi@gnu.org>, \
Adam Rogoyski <rogoyski@cs.utexas.edu>, \
Robert Siembarski <rjs@andrew.cmu.edu>, \
Rocco Corsi <rocco.corsi@sympatico.ca>, \
David Benbennick <dbenbenn@math.cornell.edu>, \
Mike Frysinger <vapier@gentoo.org>"

HOMEPAGE = "http://www.nano-editor.org"
BUGTRACKER = "https://savannah.gnu.org/bugs/?group=nano"

SECTION = "console/utils"
PRIORITY = "optional"

PROVIDES += "editor/nano"

LICENSE = "Nano GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=f27defe1e96c2e1ecd4e0c9be8967949"

DEPENDS = "ncurses"
PR = "r0"

PV_MAJOR = "${@bb.data.getVar('PV',d,1).split('.')[0]}.${@bb.data.getVar('PV',d,1).split('.')[1]}"

SRC_URI = "http://www.nano-editor.org/dist/v${PV_MAJOR}/nano-${PV}.tar.gz \
           file://ncursesw.patch"
SRC_URI[md5sum] = "af09f8828744b0ea0808d6c19a2b4bfd"
SRC_URI[sha256sum] = "b7bace9a8e543b84736d6ef5ce5430305746efea3aacb24391f692efc6f3c8d3"

inherit autotools gettext

RDEPENDS_${PN} = "ncurses"

