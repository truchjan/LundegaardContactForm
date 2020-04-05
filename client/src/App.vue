<template>
    <div id="app">
        <nav class="navbar navbar-expand navbar-dark bg-dark">
            <a href="/" class="navbar-brand">JT</a>
            <div class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a href="/add-person" class="nav-link">Add Person</a>
                </li>
                <li class="nav-item">
                    <a href="/make-request" class="nav-link">Make Request</a>
                </li>
            </div>
        </nav>

        <div class="container mt-3">
            <router-view/>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'app'
    }
</script>