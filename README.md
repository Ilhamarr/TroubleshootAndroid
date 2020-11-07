# TroubleshootAndroid
This is a mobile application of Troubeshoot.id.
for a startup computer service, it can be make client trust about this businness.

<hr>

## Developer Team
| No | NAMA           | NIM        | ROLE                |
|----|----------------|------------|---------------------|
| 1  | Vivi Rofiah    | 1313617001 | Front End Developer |
| 2  | Bagus Nugraha  | 1313617002 | Back End Developer  |
| 3  | Ilham Arrosyid | 1313617018 | Project Leader        |



## Product Backlog


### Story of Product

This Project is make mobile application of Troubleshoot.id. this app will give best experience for user to order the service easily. the step for make this application are, discuss about the business process of this app, make a design mockup in adobe XD (wireframe), Implement the design to layouting in android studio, Updating the database from website (already have a website), Make an API to connecting the database to application, Connecting all (layout, Controller, Database) same a business process that hve been discussed, Testing the application and Realease to user.

When we make this application, every sunday we have a meeting to discuss that all what a member to do in a week and give a task to do in a week forward.

#### SPRINT 3 :
in Sprint 3, we continue the task, focusing the activity login-signup-logout and ordering activity.
| Story             | TASK                                      | CONTRIBUTOR | STATUS   |
|-------------------------------------|-------------------------------------------|-------------|----------|
| Masuk ke Aplikasi  | Membuat Logical Sign in \(Create Session\)        | Bagus       | Complete |
|                   |  \(Chechk\)   session      | Bagus       | Complete |
| Membuat Account| Membuat Logical Signup                            | Bagus       | Complete |
|                   | Mengkoneksikan ke Database \(Store Data\)     | Bagus       | Complete |
| Logout Account | Check Session User pada aplikasi         | Bagus       | Complete |
| Memilih Layanan Servis   | Membuat layout order/pemesanan                         | Ilham       | Complete |



all the detail of product backlocg are in this link below : <br>
https://docs.google.com/spreadsheets/d/1l3uG52OoAbAXDz2HFyEmODxLGKXyCd1aa_SbZJHBkXs/edit#gid=1386834576



## Mockup wireframe:
mockup mobile apps Troubleshoot.id:<br>
https://github.com/Ilhamarr/Troubleshoot_Design/blob/master/Documentation.md


## REST API
the component in API that we made: 

|   | GET                                          |
|---|----------------------------------------------|
| 1 | GET list merk Laptop                         |
| 2 | GET Merk Laptop by Id                        |
| 3 | GET list layanan troubleshoot                |
| 4 | GET Layanan troubleshoot by Id               |
| 5 | GET login (butuh parameter (email,password)) |
|   |                                              |
|   | POST                                         |
| 1 | POST register akun baru                      |
| 2 | POST melakukan pemesanan jasa troubleshoot   |

the source code of the API:
https://github.com/bagusn13/RestAPITroublehoot
