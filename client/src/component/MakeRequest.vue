<template>
    <form id="submit-form" @submit.prevent>
        <!--<div v-if="!submitted">-->
        <div class="form-group">
            <label for="requestType">Kind of Request</label>
            <p>
                <select v-model="request.requestType" id="requestType">
                    <option disabled value="">Please select one</option>
                    <option value="0">Contract Adjustment</option>
                    <option value="1">Damage Case</option>
                    <option value="2">Complaint</option>
                </select>
            <div class="error" v-if="!$v.request.requestType.required">Field is required</div>

        </div>

        <div class="form-group">
            <label for="policyNumber">Policy Number</label>
            <input
                    type="text"
                    class="form-control"
                    id="policyNumber"
                    required
                    v-model="request.policyNumber"
                    name="policyNumber"
            />
            <div class="error" v-if="!$v.request.policyNumber.required">Field is required</div>
            <div class="error" v-if="!$v.request.policyNumber.isANumber">Must be a number</div>
        </div>

        <div class="form-group">
            <label for="name">Name</label>
            <input
                    type="text"
                    class="form-control"
                    id="name"
                    required
                    v-model="person.name"
                    name="name"
            />
            <div class="error" v-if="!$v.person.name.required">Field is required</div>
        </div>

        <div class="form-group">
            <label for="surname">Surname</label>
            <input
                    class="form-control"
                    id="surname"
                    required
                    v-model="person.surname"
                    name="surname"
            />
            <div class="error" v-if="!$v.person.surname.required">Field is required</div>
        </div>

        <div class="form-group-big">
            <label for="requestText">Request Text</label>
            <textarea
                    class="form-control"
                    id="requestText"
                    required
                    v-model="request.requestText"
                    name="requestText"
            ></textarea>
            <div class="error" v-if="!$v.request.requestText.required">Field is required</div>
            <div class="error" v-if="!$v.request.requestText.textLength">Text is too long</div>
            <p></p>
        </div>

        <button @click="makeRequest" class="btn btn-success">Submit</button>
        <!--</div>-->

        <!--<div v-else>-->
        <!--<h4>You submitted successfully!</h4>-->
        <!--<button class="btn btn-success" @click="newRequest">Make another request</button>-->
        <!--</div>-->
    </form>
</template>

<script>
    import {required} from "vuelidate/lib/validators";
    import {isANumber, textLength} from "./Validators"
    import PersonDataService from "../service/PersonDataService";
    import RequestDataService from "../service/RequestDataService";

    export default {
        name: "make-request",
        data() {
            return {
                request: {
                    id: null,
                    requestType: "",
                    policyNumber: "",
                    requestText: "",
                    personId: ""
                },
                person: {
                    id: null,
                    name: "",
                    surname: ""
                },
                submitted: false
            };
        },
        watch: {
            personId(value) {
                console.log(value)
            }
        },
        methods: {
            makeRequest() {
                let dataP = {
                    name: this.person.name,
                    surname: this.person.surname
                };

                if (!this.$v.person.name.required || !this.$v.person.surname.required)
                    return;

                PersonDataService.create(dataP)
                    .then(response => {
                        this.request.personId = response.data.id
                    })
                    .then(this.saveRequest);

                //todo case: person of the name already exists - can be same name or different id....
                //let per = PersonDataService.findByNameAndSurname(dataP.name, dataP.surname);

            },

            saveRequest() {
                let data = {
                    requestType: this.request.requestType,
                    policyNumber: this.request.policyNumber,
                    requestText: this.request.requestText
                };

                if (!this.$v.request.requestType.required || !this.$v.request.policyNumber.required
                    || !this.$v.request.requestText.required || !this.$v.request.policyNumber.isANumber
                    || !this.$v.request.requestText.textLength)
                    return;

                RequestDataService.create(this.request.personId, data)
                    .then(response => {
                        this.request.id = response.data.id;
                        console.log(response.data);
                        this.submitted = true;
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },

            newRequest() {
                this.submitted = false;
                this.request = {};
            }
        },
        validations: {
            request: {
                requestType: {required},
                policyNumber: {required, isANumber},
                requestText: {required, textLength}
            },
            person: {
                name: {required},
                surname: {required}
            }
        }
    };
</script>

<style>
    .form-group {
        max-width: 300px;
    }

    .error {
        color: red;
    }

    .form-group-big textarea {
        height: 200px;
    }
</style>