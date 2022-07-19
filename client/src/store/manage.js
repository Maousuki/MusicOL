export default {
  state: {
    url: "",
    id: "",
    breadcrumbList: [],
    collapse: false,
  },
  getters: {
    url: (state) => state.url,
    id: (state) => state.id,
    breadcrumbList: (state) => state.breadcrumbList,
    collapse: (state) => state.collapse,
  },
  mutations: {
    setUrl: (state, url) => {
      state.url = url;
    },
    setId: (state, id) => {
      state.id = id;
    },
    setBreadcrumbList: (state, breadcrumbList) => {
      state.breadcrumbList = breadcrumbList;
    },
    setCollapse: (state, collapse) => {
      state.collapse = collapse;
    }
  },
};
