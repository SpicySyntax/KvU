# KvU

Interactive virtual tours of the University Kansas campus

## KvU was a Java Android app that allowed a user to navigate KU (University of Kansas) campus using Virtual Reality and Voice Commands
    - Uses a map of stored location points and paths in our SQL database to support user navigation
    - streams 360 degree video from Red5Pro Server to the Google VR panorama widget,
        allowing the user to examine their surroundings around KU.
    - Harnesses Microsofts Cognitive Speech api to allow the user to navigate with voice commands.

### Note: the Red5Pro Server and SQL Database are no longer running and this project will not work as is.

## Python automation Scripts (Python 2.7.*): Python scripts to initialize  and manipulate SQL database and preparing video files for Http live streaming
- HLS_VIDEO_FORNATTING: Script for converting mp4s on the Video streaming servie to m3u8 (must be run on ubuntu machine with ffmeg installed)
- XL_SHEET_DATA_COLLECTION: parses location data and populates SQL data base. 
