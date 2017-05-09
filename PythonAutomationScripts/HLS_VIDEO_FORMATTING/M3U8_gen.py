import subprocess
class M3U8_gen(object):
	def run(self):
		proc = subprocess.Popen(['ls','-a'], stdout=subprocess.PIPE)
		proc.wait()
		lines = proc.stdout.readlines()
		mp4s = []
		for line in lines:
			if ".mp4" in line:
				mp4s.append(line[:-1])
				print(line)
		print "Found all mp4s"
		for mp4 in mp4s:
			cmd = ['sudo','ffmpeg','-i', mp4,'-strict', '-2', '-hls_time', '5', mp4[:-4]+'.m3u8']
			print cmd
			proc = subprocess.Popen(cmd,stdout=subprocess.PIPE)
			proc.wait()
			
		
if __name__ == '__main__':
	M3U8_gen().run()

