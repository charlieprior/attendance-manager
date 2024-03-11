# Class design list
## Server:
- User
- Class
- Student
- AttendanceRecord
- WeeklyAttendanceRecord (extends from AttendanceRecord)
- LoginRequest
- Notifier
 - EmailNotifier
- SaveData
- LoadData

## Client:
### Model:
*Some classes can be shared with server. DRY*
- Class
- Student
- AttendanceRecord
- WeeklyAttendanceRecord (extends from AttendanceRecord)

### View:
- ClassView
  - ClassTextView *We may have to create a GUI in the future, so better to be extensible now*

### Controller:
- User
 - TextUser
