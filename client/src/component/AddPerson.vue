<template>
    <form id="submit-form" @submit.prevent>
        <!--<p v-if="!submitted">-->
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
            <div class="error" v-if="!$v.person.name.required">Name is required</div>
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
            <div class="error" v-if="!$v.person.surname.required">Surname is required</div>
        </div>

        <button @click="savePerson" class="btn btn-success">Submit</button>
        <!--</div>-->

        <!--<div v-else>-->
        <!--<h4>You submitted successfully!</h4>-->
        <!--<button class="btn btn-success" @click="newPerson">Add another person</button>-->
        <!--</div>-->
    </form>
</template>

<script>
    import {required} from "vuelidate/lib/validators";
    import PersonDataService from "../service/PersonDataService";

    export default {
        name: "add-person",
        data() {
            return {
                person: {
                    id: null,
                    name: "",
                    surname: ""
                },
                submitted: false
            };
        },
        methods: {
            savePerson() {
                let data = {
                    name: this.person.name,
                    surname: this.person.surname
                };

                if (!this.$v.person.name.required || !this.$v.person.surname.required)
                    return;

                PersonDataService.create(data)
                    .then(response => {
                        this.person.id = response.data.id;
                        console.log(response.data);
                        this.submitted = true;
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },

            newPerson() {
                this.submitted = false;
                this.person = {};
            }
        },
        validations: {
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
</style>