class Path(object):
    start = None
    end = None
    name = None
    def __init__(self, start, end, name):
        self.start = start
        self.end = end
        self.name = name
    def get_db_string(self, pk):
        ret = "("+str(pk)+", '"+self.start+"','"+self.end+"','"+self.name+"',NULL)"
        return ret
