class Point(object):
    name = None
    lat = None
    lng = None
    next_pts = None
    entry = None;
    def __init__(self, name, lat, lng, next_pts,entry):
        self.name = name
        self.lat = lat
        self.lng = lng
        self.next_pts = next_pts
        self.entry = entry
    def get_db_string(self, pk):
        ret = "("+str(pk)+", '"+self.name+"', "+str(self.lat)+", "+ str(self.lng) + ", '"+self.next_pts+"', "+str(self.entry)+")"
        return ret
    def get_string(self):
        ret = "('"+self.name+"', "+str(self.lat)+", "+ str(self.lng) + ", '"+self.next_pts+"', "+str(self.entry)+")"
        return ret
