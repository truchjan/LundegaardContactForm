import http from "../http-common";

class RequestDataService {
    getAll(personId) {
        return http.get(`/person/${personId}/request/all`);
    }

    get(personId, requestId) {
        return http.get(`/person/${personId}/request/${requestId}`);
    }

    create(personId, data) {
        return http.post(`/person/${personId}/request`, data);
    }

    update(personId, requestId, data) {
        return http.put(`/person/${personId}/request/${requestId}`, data);
    }

    delete(personId, requestId) {
        return http.delete(`/person/${personId}/request/${requestId}`);
    }
}

export default new RequestDataService();