# KvU: interactive virtual tours of KU campus
## KvU is an Android app that allows a user to navigate KU campus:
    - Uses a map of stored points and paths in our SQL database to help user navigate
    - streams 360 video to the Google VR panorama widget for the user to examine their surroundings when walking around
    - Harnesses Microsofts Cognitive Speech api to allow the user to navigate with voice commands/

### Note: the Red5Pro Server and SQL Database are no longer running and this project will not work as is.
## Python automation Scripts (Python 2.7.*)
- HLS_VIDEO_FORNATTING: Script for converting mp4s on the Video streaming servie to m3u8 (must be run on ubuntu machine with ffmeg installed)
- XL_SHEET_DATA_COLLECTION: parses location data and populates SQL data base. 
