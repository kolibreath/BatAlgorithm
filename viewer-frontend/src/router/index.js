import Vue from 'vue';
import VueRouter from 'vue-router';

import Canvas from '../components/Canvas.vue';
import Dashboard from '../components/Dashboard.vue';
import Step from '../components/Step.vue';

Vue.use(VueRouter);

const routes = [
	{
		path: '/canvas',
		component: Canvas
	},
	{
		path: '/dashboard',
		component: Dashboard
	},
	{
		path: '/step',
		component: Step
	}
];

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
});

export default router;
