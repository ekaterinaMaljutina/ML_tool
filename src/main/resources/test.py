import time
import argparse

import functools
print = functools.partial(print, flush=True)
for i in range(2):
	print("%s hello" % i)
	time.sleep(5)