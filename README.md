# Heartbeat EMR Web Services
All endpoints documented below are protected and require the use of a BASIC AUTH header with a correct username and password.

## Patient Endpoints
The patient web services use HATEOAS standards for aiding in self discovery of the endpoints within the web service.  The patient web services use REST conventions for listing, updating, creating, and deleting patient data.  Only users with the role USER may access these endpoints.

### GET - http://jbossews-projectemr.rhcloud.com/emr/patient{?page,size,sort}

Provides a listing of all patients in a paged format.

Query Parameters:
* Size integer - The size of the page (default 20).
* Page integer - The page number to view (default 0).
* Sort asc/desc - The sort order of the data by ID (default asc).

### POST - http://jbossews-projectemr.rhcloud.com/emr/patient

Add a new patient to the database.

Example Request BODY:
```
{
    "firstName": "Jackie",
    "lastName": "Acosta",
    "middleName": "Lee",
    "height": 123,
    "weight": 321,
    "birthdate": "10/17/1984",
    "socialSecurityNumber": "1248637",
    "primaryLanguage": "English",
    "statusCode": "Full Code",
    "address": {
        "streetAddress": "2423 Via Mariposa",
        "streetAddress2": "",
        "city": "San Dimas",
        "state": "CA",
        "zipCode": 91773
    },
    "phoneNumber": {
        "number": "626-447-4444",
        "type": "Home"
    },
    "insurance": {
        "name": "Blue Shield",
        "phoneNumber": "626-555-4871",
        "policyNumber": "45157123A"
    },
    "emergencyContact": {
        "name": "Robert",
        "phoneNumber": "949-555-4433",
        "relationship": "Brother"
    },
    "livesAt": "Home (alone)",
    "hasWill": false,
    "hasAdvancedDirective": false,
    "hasFluShot": true,
    "fluShotDate": "11/15/2015",
    "hasPneumoniaShot": true,
    "pneumoniaShotDate": "12/12/2012"
}
```
Response Status Code - 201

### GET - http://jbossews-projectemr.rhcloud.com/emr/patient/[id]
Returns the data about one specific patient.

URL params:
* ID string - A UUID created by the database to uniquely identify a patient

Response:
```
{
    "id": "54316465e4b0590f8a3b5bad",
    "firstName": "Jackie",
    "lastName": "Acosta",
    "middleName": "Lee",
    "height": 123,
    "weight": 321,
    "birthdate": "10/17/1984",
    "socialSecurityNumber": "1248637",
    "primaryLanguage": "English",
    "statusCode": "Full Code",
    "address": {
        "streetAddress": "2423 Via Mariposa",
        "streetAddress2": "",
        "city": "San Dimas",
        "state": "CA",
        "zipCode": 91773
    },
    "phoneNumber": {
        "number": "626-447-4444",
        "type": "Home"
    },
    "insurance": {
        "name": "Blue Shield",
        "phoneNumber": "626-555-4871",
        "policyNumber": "45157123A"
    },
    "emergencyContact": {
        "name": "Robert",
        "phoneNumber": "949-555-4433",
        "relationship": "Brother"
    },
    "livesAt": "Home (alone)",
    "hasWill": false,
    "hasAdvancedDirective": false,
    "hasFluShot": true,
    "fluShotDate": "11/15/2015",
    "hasPneumoniaShot": true,
    "pneumoniaShotDate": "12/12/2012",
    "assessments": null,
    "_links": {
        "self": {
            "href": "https://jbossews-projectemr.rhcloud.com/emr/patient/54316465e4b0590f8a3b5bad"
        }
    }
}
```
Response Status Code - 200

### PUT - http://jbossews-projectemr.rhcloud.com/emr/patient/[id]
Updates the data about one specific patient.

URL params:
* ID string - A UUID created by the database to uniquely identify a patient

Request BODY:
```
See Request BODY from POST example above
```
Response Status Code - 204

### DELETE - http://jbossews-projectemr.rhcloud.com/emr/patient/[id]
Deletes a specific patient from the database.

URL params:
* ID string - A UUID created by the database to uniquely identify a patient

Response Status Code - 204

### GET - https://jbossews-projectemr.rhcloud.com/emr/patient/search/findByFirstNameAndLastName{?first_name,last_name}
Allows for searching for specific patients by their first and last name.

Query parameters:
* first_name string - First name of a patient
* last_name string - Last name of a patient

Example Response:
```
{
  "_embedded" : {
    "patient" : [ {
      "id" : "54162d6ce4b005ec3cdc35dc",
      "firstName" : "Robert",
      "lastName" : "Acosta",
      "middleName" : "Joseph",
      "height" : 75,
      "weight" : 80,
      "birthdate" : "01/26/1983",
      "socialSecurityNumber" : "214511234",
      "primaryLanguage" : "English",
      "statusCode" : "Full Code",
      "address" : {
        "streetAddress" : "8 Woodswallow Ln",
        "streetAddress2" : "",
        "city" : "Aliso Viejo",
        "state" : "CA",
        "zipCode" : 92656
      },
      "phoneNumber" : {
        "number" : "(949) 689-4444",
        "type" : "Work"
      },
      "insurance" : {
        "name" : "Blue Shield",
        "phoneNumber" : "",
        "policyNumber" : "(656) 242-3854"
      },
      "emergencyContact" : {
        "name" : "Marissa Reeber",
        "phoneNumber" : "(626) 845-2312",
        "relationship" : "Spouse"
      },
      "livesAt" : "Home (alone)",
      "hasWill" : true,
      "hasAdvancedDirective" : true,
      "hasFluShot" : true,
      "fluShotDate" : "07/11/2010",
      "hasPneumoniaShot" : true,
      "pneumoniaShotDate" : "03/14/2008",
      "assessments" : [ "54f3c881e4b0c21fce8abd40", "54f3cb21e4b0c21fce8abd41" ],
      "_links" : {
        "self" : {
          "href" : "https://jbossews-projectemr.rhcloud.com/emr/patient/54162d6ce4b005ec3cdc35dc"
        }
      }
    } ]
  }
}
```
Response Code - 200

### GET - https://jbossews-projectemr.rhcloud.com/emr/patient/search/findByLastName{?last_name}
Allows for searching of patients with only their last name.

Query parameters:
* last_name string - Last name of a patient

Response:
```
See response from findByFirstNameAndLastName
```

## Assessment Endpoints
The assessment web services use HATEOAS standards for aiding in self discovery of the endpoints within the web service.  The assessment web services use REST conventions for listing, updating, creating, and deleting assessment data.  Only users with the role USER may access these endpoints.

### GET - http://jbossews-projectemr.rhcloud.com/emr/assessment[?size=1[&page=0]][&sort=asc]
Provides a listing of all assessments in a paged format.

Query Parameters:
* Size integer - The size of the page (default 20).
* Page integer - The page number to view (default 0).
* Sort asc/desc - The sort order of the data by ID (default asc). 

### POST - http://jbossews-projectemr.rhcloud.com/emr/assessment
Add a new assessment to the database.

Example Request BODY:
```
{
  "date" : "03/01/2015",
  "time" : "18:29",
  "neurological" : {
    "levelOfConsciousness" : "Conscious",
    "orientation" : "x4 - person, place, time, situation",
    "rightPupil" : {
      "size" : 2,
      "reaction" : "Normal"
    },
    "leftPupil" : {
      "size" : 2,
      "reaction" : "Normal"
    },
    "upperExtremity" : {
      "left" : "+5 - able to move against full resistance",
      "right" : "+5 - able to move against full resistance"
    },
    "lowerExtremity" : {
      "left" : "+5 - able to move against full resistance",
      "right" : "+5 - able to move against full resistance"
    }
  },
  "respiratory" : {
    "leftBreathSounds" : {
      "anterior" : "Clear",
      "posterior" : "Clear"
    },
    "rightBreathSounds" : {
      "anterior" : "Clear",
      "posterior" : "Clear"
    },
    "respirations" : {
      "noDistress" : false,
      "labored" : false,
      "shortnessOfBreath" : false
    },
    "chestExcursion" : "Symmetrical",
    "cough" : {
      "present" : false,
      "productive" : false
    }
  },
  "cardio" : {
    "radialPulse" : {
      "left" : "Strong",
      "right" : "Strong"
    },
    "pedalPulse" : {
      "left" : "Strong",
      "right" : "Strong"
    },
    "capillaryRefill" : "Less than 2 sec",
    "temperature" : {
      "warm" : true,
      "clammy" : false,
      "diaphoretic" : false
    },
    "rightArm" : {
      "level" : 0,
      "pitting" : false
    },
    "leftArm" : {
      "level" : 0,
      "pitting" : false
    },
    "rightLeg" : {
      "level" : 0,
      "pitting" : false
    },
    "leftLeg" : {
      "level" : 0,
      "pitting" : false
    },
    "jvd" : false,
    "rhythm" : ""
  },
  "gastrointestinal" : {
    "bowelSounds" : "Normal",
    "turgor" : "Normal",
    "abdomen" : {
      "feeling" : "Soft",
      "distended" : false
    },
    "skin" : "Normal",
    "urine" : {
      "color" : "Yellow",
      "character" : "Clear",
      "foleyCatheter" : false
    },
    "stoolContinent" : true,
    "nausea" : false,
    "emesis" : false
  },
  "skin" : {
    "incisions" : [ {
      "site" : "leg",
      "wellApproximated" : false,
      "woundOpen" : false,
      "redness" : false,
      "drainage" : false,
      "swelling" : true,
      "dressingIntact" : true,
      "steriStripped" : false,
      "staplesSutures" : false
    } ],
    "breakdowns" : [ {
      "site" : "head",
      "drainage" : false,
      "redness" : true,
      "dressing" : false,
      "stage" : ""
    } ]
  }
}
```
Response Status Code - 201

### GET - http://jbossews-projectemr.rhcloud.com/emr/assessment/[id]
Returns the data about one specific assessment.

URL params:
* ID string - A UUID created by the database to uniquely identify an assessment

Response:
```
{
  "id" : "54f3cb21e4b0c21fce8abd41",
  "date" : "03/01/2015",
  "time" : "18:29",
  "neurological" : {
    "levelOfConsciousness" : "Conscious",
    "orientation" : "x4 - person, place, time, situation",
    "rightPupil" : {
      "size" : 2,
      "reaction" : "Normal"
    },
    "leftPupil" : {
      "size" : 2,
      "reaction" : "Normal"
    },
    "upperExtremity" : {
      "left" : "+5 - able to move against full resistance",
      "right" : "+5 - able to move against full resistance"
    },
    "lowerExtremity" : {
      "left" : "+5 - able to move against full resistance",
      "right" : "+5 - able to move against full resistance"
    }
  },
  "respiratory" : {
    "leftBreathSounds" : {
      "anterior" : "Clear",
      "posterior" : "Clear"
    },
    "rightBreathSounds" : {
      "anterior" : "Clear",
      "posterior" : "Clear"
    },
    "respirations" : {
      "noDistress" : false,
      "labored" : false,
      "shortnessOfBreath" : false
    },
    "chestExcursion" : "Symmetrical",
    "cough" : {
      "present" : false,
      "productive" : false
    }
  },
  "cardio" : {
    "radialPulse" : {
      "left" : "Strong",
      "right" : "Strong"
    },
    "pedalPulse" : {
      "left" : "Strong",
      "right" : "Strong"
    },
    "capillaryRefill" : "Less than 2 sec",
    "temperature" : {
      "warm" : true,
      "clammy" : false,
      "diaphoretic" : false
    },
    "rightArm" : {
      "level" : 0,
      "pitting" : false
    },
    "leftArm" : {
      "level" : 0,
      "pitting" : false
    },
    "rightLeg" : {
      "level" : 0,
      "pitting" : false
    },
    "leftLeg" : {
      "level" : 0,
      "pitting" : false
    },
    "jvd" : false,
    "rhythm" : ""
  },
  "gastrointestinal" : {
    "bowelSounds" : "Normal",
    "turgor" : "Normal",
    "abdomen" : {
      "feeling" : "Soft",
      "distended" : false
    },
    "skin" : "Normal",
    "urine" : {
      "color" : "Yellow",
      "character" : "Clear",
      "foleyCatheter" : false
    },
    "stoolContinent" : true,
    "nausea" : false,
    "emesis" : false
  },
  "skin" : {
    "incisions" : [ {
      "site" : "leg",
      "wellApproximated" : false,
      "woundOpen" : false,
      "redness" : false,
      "drainage" : false,
      "swelling" : true,
      "dressingIntact" : true,
      "steriStripped" : false,
      "staplesSutures" : false
    } ],
    "breakdowns" : [ {
      "site" : "head",
      "drainage" : false,
      "redness" : true,
      "dressing" : false,
      "stage" : ""
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "https://jbossews-projectemr.rhcloud.com/emr/assessment/54f3cb21e4b0c21fce8abd41"
    }
  }
}
```
Response Status Code - 200

### PUT - http://jbossews-projectemr.rhcloud.com/emr/assessment/[id]
Updates the data about one specific assessment.

URL params:
* ID string - A UUID created by the database to uniquely identify an assessment

Request BODY:
```
See Request BODY from POST example above
```
Response Status Code - 204

### DELETE - http://jbossews-projectemr.rhcloud.com/emr/assessment/[id]
Deletes a specific assessment from the database.
URL params:
* ID string - A UUID created by the database to uniquely identify an assessment
Response Status Code - 204

## User Endpoints
The user web services use HATEOAS standards for aiding in self discovery of the endpoints within the web service.  The user web services use REST conventions for listing, updating, creating, and deleting user data.  Only users with the role ADMIN may access these endpoints.

### GET - http://jbossews-projectemr.rhcloud.com/emr/user[?size=1[&page=0]][&sort=asc]
Provides a listing of all users in a paged format.

Query Parameters:
* Size integer - The size of the page (default 20).
* Page integer - The page number to view (default 0).
* Sort asc/desc - The sort order of the data by ID (default asc). 

### POST - http://jbossews-projectemr.rhcloud.com/emr/user
Add a new user to the database.

Example Request BODY:
```
{
  "firstName" : "Robert",
  "lastName" : "Acosta",
  "username" : "admin",
  "password" : "password",
  "jobTitle" : "Developer",
  "role" : "ROLE_ADMIN",
  "email" : "robert.j.acosta@gmail.com",
  "lastPasswordChange" : null,
  "enabled" : true
}
```
Response Status Code - 201

### GET - http://jbossews-projectemr.rhcloud.com/emr/user/[id]
Returns the data about one specific user.

URL params:
* ID string - An ID created by the database to uniquely identify an user

Response:
```
{
  "firstName" : "Robert",
  "lastName" : "Acosta",
  "username" : "admin",
  "password" : "dkfjaldru83ij3i4j2oieqrje2ekopl028w",
  "jobTitle" : "Developer",
  "role" : "ROLE_ADMIN",
  "email" : "robert.j.acosta@gmail.com",
  "lastPasswordChange" : null,
  "enabled" : true,
  "patientIds" : [ ],
  "_links" : {
    "self" : {
      "href" : "https://jbossews-projectemr.rhcloud.com/emr/user/1"
    }
  }
}
```
Response Status Code - 200

### PUT - http://jbossews-projectemr.rhcloud.com/emr/user/[id]
Updates the data about one specific user.

URL params:
* ID string - An ID created by the database to uniquely identify an user

Request BODY:
```
{
  "id": 1,
  "firstName" : "Robert",
  "lastName" : "Acosta",
  "username" : "admin",
  "password" : "dkfjaldru83ij3i4j2oieqrje2ekopl028w",
  "jobTitle" : "Developer",
  "role" : "ROLE_ADMIN",
  "email" : "robert.j.acosta@gmail.com",
  "lastPasswordChange" : null,
  "enabled" : true,
  "patientIds" : [ ],
  "_links" : {
    "self" : {
      "href" : "https://jbossews-projectemr.rhcloud.com/emr/user/1"
    }
  }
}
```
Response Status Code - 204

### DELETE - http://jbossews-projectemr.rhcloud.com/emr/user/[id]
Deletes a specific user from the database.

URL params:
* ID string - An ID created by the database to uniquely identify an user

Response Status Code - 204

### GET - https://jbossews-projectemr.rhcloud.com/emr/user/search/findByUsername{?username}
Request for a list of users searching by username.

Query parameter:
* username string - Username to search for

Response:
```
 "_embedded" : {
    "user" : [ {
      "firstName" : "Robert",
      "lastName" : "Acosta",
      "username" : "racosta",
      "password" : "dkfjaldru83ij3i4j2oieqrje2ekopl028w",
      "jobTitle" : "Developer",
      "role" : "ROLE_USER",
      "email" : "robert.j.acosta@gmail.com",
      "lastPasswordChange" : "09/27/2014",
      "enabled" : true,
      "patientIds" : [ "https://jbossews-projectemr.rhcloud.com/emr/patient/54162d6ce4b005ec3cdc35dc", "https://jbossews-projectemr.rhcloud.com/emr/patient/54316465e4b0590f8a3b5bad" ],
      "_links" : {
        "self" : {
          "href" : "https://jbossews-projectemr.rhcloud.com/emr/user/3"
        }
      }
    } ]
  }
}
```
Response Status Code - 200

### GET - https://jbossews-projectemr.rhcloud.com/emr/user/search/findByRole{?role}
Gets a list of users by their role.

Query parameter:
* role string - A role such as ROLE_USER

Response:
```
{
  "_embedded" : {
    "user" : [ {
      "firstName" : "Marissa",
      "lastName" : "Acosta",
      "username" : "macosta",
      "password" : "dkfjaldru83ij3i4j2oieqrje2ekopl028w",
      "jobTitle" : "Registered Nurse",
      "role" : "ROLE_USER",
      "email" : "mreeber@gmail.com",
      "lastPasswordChange" : "09/27/2014",
      "enabled" : true,
      "patientIds" : [ "https://jbossews-projectemr.rhcloud.com/emr/patient/54162d6ce4b005ec3cdc35dc" ],
      "_links" : {
        "self" : {
          "href" : "https://jbossews-projectemr.rhcloud.com/emr/user/2"
        }
      }
    }, {
      "firstName" : "Robert",
      "lastName" : "Acosta",
      "username" : "racosta",
      "password" : "dkfjaldru83ij3i4j2oieqrje2ekopl028w",
      "jobTitle" : "Developer",
      "role" : "ROLE_USER",
      "email" : "robert.j.acosta@gmail.com",
      "lastPasswordChange" : "09/27/2014",
      "enabled" : true,
      "patientIds" : [ "https://jbossews-projectemr.rhcloud.com/emr/patient/54162d6ce4b005ec3cdc35dc", "https://jbossews-projectemr.rhcloud.com/emr/patient/54316465e4b0590f8a3b5bad" ],
      "_links" : {
        "self" : {
          "href" : "https://jbossews-projectemr.rhcloud.com/emr/user/3"
        }
      }
    } ]
  }
}
```
Response Status Code - 200

## Authorized Web Services
The authorized web services associate the data from the user, patient, and assessment web services.  These endpoints follow REST conventions but do not follow HATEOAS and are thus not self-discoverable.  Admins and Users may access these endpoints.

### GET - https://jbossews-projectemr.rhcloud.com/emr/authorized/verify?username
Used to verify a username belongs to the system at login.

Query parameters:
* username string REQUIRED - The username of the user attempting to login.

Response:
```
{
    "id": 3,
    "firstName": "Robert",
    "lastName": "Acosta",
    "username": "racosta",
    "password": "dkfjaldru83ij3i4j2oieqrje2ekopl028w",
    "jobTitle": "Developer",
    "role": "ROLE_USER",
    "email": "robert.j.acosta@gmail.com",
    "lastPasswordChange": "09/27/2014",
    "enabled": true,
    "patientIds": [
        "https://jbossews-projectemr.rhcloud.com/emr/patient/54162d6ce4b005ec3cdc35dc",
        "https://jbossews-projectemr.rhcloud.com/emr/patient/54316465e4b0590f8a3b5bad"
    ]
}
```
Response Status Code - 200

### GET - https://jbossews-projectemr.rhcloud.com/emr/authorized/patients?userid
Returns a listing of patients assigned to a specific user.

Query parameters:
* userid int REQUIRED - The user ID that we wish to get assigned patients list

Response:
```
A listing of patients (see single patient response above)
```
Response Status Code - 200

### POST - https://jbossews-projectemr.rhcloud.com/emr/authorized/patients
Adds a patient to a user’s roster.

Request BODY:
```
{
    "userId": 3,
    "patientId": "54316465e4b0590f8a3b5bad"
}
```
Response:
```
A user with patient added (see user response above)
```
Response Status Code - 201

### PUT - https://jbossews-projectemr.rhcloud.com/emr/authorized/patients
Removes a patient from a user’s roster.

Request BODY:
```
{
    "userId": 3,
    "patientId": "54316465e4b0590f8a3b5bad"
}
```
Response:
```
A user with patient added (see user response above)
```
Response Status Code - 200

### GET - https://jbossews-projectemr.rhcloud.com/emr/authorized/assessments?patientid
Returns a listing of assessments for a specific patient

Query Parameters:
* patientid string REQUIRED - The UUID of the patient

Response:
```
A listing of assessments (see single assessment response above)
```
Response Status Code - 200

### POST - https://jbossews-projectemr.rhcloud.com/emr/authorized/assessment
Creates an assessment and associates it to a patient

Request Body:
```
{
    "patientid": "54316465e4b0590f8a3b5bad",
    "assessment": <assessment request body>
}
```
Response:
```
An assessment (see single assessment response above)
```
Response Status Code - 201

### PUT - https://jbossews-projectemr.rhcloud.com/emr/authorized/assessment
Deletes an assessment and disassociates it from a patient

Request Body:
```
{
    "patientid": "54316465e4b0590f8a3b5bad",
    "assessment": <assessment request body>
}
```
Response:
```
A patient (see single patient response above)
```
Response Status Code - 200

The OpenShift `jbossews` cartridge documentation can be found at:

http://openshift.github.io/documentation/oo_cartridge_guide.html#tomcat
