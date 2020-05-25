#!"C:\Users\jakub\Desktop\Distributed_systems\lab4\homework\homework 4.2\Client\venv\Scripts\python.exe"
# EASY-INSTALL-ENTRY-SCRIPT: 'zeroc-ice==3.7.3','console_scripts','slice2py'
__requires__ = 'zeroc-ice==3.7.3'
import re
import sys
from pkg_resources import load_entry_point

if __name__ == '__main__':
    sys.argv[0] = re.sub(r'(-script\.pyw?|\.exe)?$', '', sys.argv[0])
    sys.exit(
        load_entry_point('zeroc-ice==3.7.3', 'console_scripts', 'slice2py')()
    )
