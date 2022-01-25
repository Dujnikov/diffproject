Summary:		Aladdin Enterprise Certificate Authority
Name:			aeca_name
Version:		aeca_version
Release:		aeca_release

Group:			System/Servers

Vendor:			Aladdin R.D.
Packager:		Aladdin R.D.
License:		Commercial licence
URL:            https://www.aladdin-rd.aeca/

Source0:        aeca-dist.tar.gz
Requires: 		tar, unzip, java-1.8.0-openjdk-devel, ant, psmisc, bc, patch

%description
Aladdin Enterprise Certificate Authority. Only for internal testing purposes.

%prep
rm -rf *
tar -zxf %{SOURCE0}
ls

%build
# we have no source, so nothing to do here

%install
if [ -d %buildroot/opt/aeca-dist ]; then
    rm -rf %buildroot/opt/aeca-dist
fi

mkdir -p %buildroot/opt/aeca-dist
cp -r ./* %buildroot/opt/aeca-dist/

%files
%defattr(755,root,root,755)
/opt/aeca-dist/*