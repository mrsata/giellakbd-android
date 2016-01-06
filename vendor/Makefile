LIBS=xz/configure libarchive/configure hfst-ospell/configure

all: $(LIBS)
	abuild -o ../libs abuild.json
	cp hfst-ospell/libhfstospell.jar ../libs

xz/configure:
	cd $(dir $@) && ./autogen.sh 1>/dev/null

libarchive/configure:
	cd $(dir $@) && ./build/autogen.sh 1>/dev/null

hfst-ospell/configure:
	cd $(dir $@) && ./autogen.sh 1>/dev/null

clean:
	for i in $(dir $(LIBS)); do pushd $$i; git clean -fdx; popd; done
