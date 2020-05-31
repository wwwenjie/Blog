import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    snackCon: {
      show: undefined,
      text: 'Default Text',
      type: 'info',
      confirmText: 'Okay',
      declineText: 'No',
      callbackConfirm: () => {},
      callbackDecline: () => {},
      timeout: 0
    },
    articles: require('../data/articles.json'),
    drawer: false,
    items: [
      {
        text: 'Home',
        href: '#'
      },
      {
        text: 'About',
        href: ''
      }
    ]
  },
  getters: {
    categories: state => {
      const categories = []

      for (const article of state.articles) {
        if (
          !article.category ||
          categories.find(category => category.text === article.category)
        ) continue

        const text = article.category

        categories.push({
          text,
          href: '#!'
        })
      }

      return categories.sort().slice(0, 4)
    },
    links: (state, getters) => {
      return state.items.concat(getters.categories)
    }
  },
  mutations: {
    setDrawer: (state, payload) => (state.drawer = payload),
    toggleDrawer: state => (state.drawer = !state.drawer),
    setSnackCon: (state, payload) => (state.snackCon = payload)
  },
  actions: {

  }
})
