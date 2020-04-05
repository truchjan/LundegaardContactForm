import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
    mode: "history",
    routes: [
        //todo pahts for other featuers like PeopleList(getAll) or Person(update, delete)
        {
            path: "/add-person",
            name: "add-person",
            component: () => import("./component/AddPerson")
        },
        {
            path: "/make-request",
            name: "make-request",
            component: () => import("./component/MakeRequest")
        }
    ]
});