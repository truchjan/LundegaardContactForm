import http from "../http-common";

class PersonDataService {
    getAll() {
        return http.get("/person/all");
    }

    get(id) {
        return http.get(`/person/${id}`);
    }

    create(data) {
        return http.post("/person", data);
    }

    update(id, data) {
        return http.put(`/person/${id}`, data);
    }

    delete(id) {
        return http.delete(`/person/${id}`);
    }

    findByNameAndSurname(name, surname) {
        return http.get(`/person?name=${name}&surname=${surname}`);
    }
}

export default new PersonDataService();