import pyodbc

class Db_helper(object):
    con_str = ""
    cnxn = None
    db_name = ""
    def __init__(self, db_name):
        server = '52.88.153.136'
        database = 'KvU'
        username = 'remoteSQL'
        password = '<5s4q2l>'
        driver= '{SQL Server}'

        self.db_name = db_name
        self.con_str = 'DRIVER='+driver+';PORT=1433;SERVER='+server+';PORT=1434;DATABASE='+database+';UID='+username+';PWD='+ password
        self.cnxn = pyodbc.connect(self.con_str)
        if( self.cnxn == None ):
            print 'error connecting to server'
    def set_db_name(self, db_name):
        self.db_name = db_name

    def get_pk(self,table_name):
        cursor = self.cnxn.cursor()
        query = "SELECT MAX(PK_INT) from "+table_name+"_"+self.db_name+";"
        cursor.execute(query)
        #weird syntax for ret value
        (val, ) = cursor.fetchone()
        if val == None:
            return 1
        else:
            return val+1
    def insert_points(self, points):
        #Must first find top PK and assign to this PK+1
        #print self.get_last_pk()

        new_pk = self.get_pk("POINTS")
        cursor = self.cnxn.cursor()
        query = "INSERT INTO POINTS_"+self.db_name+" (PK_INT, PT_NAME, LAT, LONG, NEXT_PT, ENTRY) \nVALUES "
        vals = ""
        i = 0
        for point in points:
            vals +=  point.get_db_string(new_pk+i)+","
            i+=1
        #remove last comma
        vals = vals[:-1]
        vals += ";"
        query += vals
        print query
        cursor.execute(query)
        print 'query exectuted'
        self.cnxn.commit()
    def purge_points(self):
        self.cnxn.cursor().execute("delete from POINTS_"+self.db_name+";")
        self.cnxn.commit()
    def purge_paths(self):
        self.cnxn.cursor().execute("delete from PATHS_"+self.db_name+";")
        self.cnxn.commit()
    def insert_paths(self, paths):
        new_pk = self.get_pk('PATHS')
        cursor = self.cnxn.cursor()
        query = "INSERT INTO PATHS_"+self.db_name+" (PK_INT, START_PT, END_PT, PATH_NAME, MEDIA) \nVALUES "
        vals = ""
        i = 0
        for path in paths:
            vals +=  path.get_db_string(new_pk+i)+","
            i+=1
        #remove last comma
        vals = vals[:-1]
        vals += ";"
        query += vals
        print query
        cursor.execute(query)
        print 'query exectuted'
        self.cnxn.commit()

    def print_table(self, name):
        cursor = self.cnxn.cursor()
        cursor.execute("Select * from "+name+"_"+self.db_name+";")
        rows = cursor.fetchall()
        for row in rows:
            print row
