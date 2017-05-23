from __future__ import print_function
import time
import sys


def print_(s, end='\n', file=sys.stdout):
    file.write(s + end)
    file.flush()


sys.stdout.flush()
for i in range(5):
    print_("%s hello" % i)
    time.sleep(5)
