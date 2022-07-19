import {createStore} from 'vuex'
import configure from "./configure";
import user from "./user";
import song from "./song";
import manage from "./manage";

const store = createStore({
    modules: {
        configure,
        user,
        song,
        manage
    }
})

export default store