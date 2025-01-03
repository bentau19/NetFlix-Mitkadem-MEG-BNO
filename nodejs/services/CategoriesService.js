const Categories = require('../models/categories');
const createCategories = async (name, promoted) => {
const categories = new Categories({ name : name});
if (promoted) categories.promoted = promoted;
return await categories.save();
};
const getCategoriesById = async (id) => { return await Categories.findById(id); };
const getCategories = async () => { return await Categories.find({}); };
const updateCategories = async (id, name,promoted) => {
const categories = await getCategoriesById(id);
if (!categories) return null;
categories.name = name;
categories.promoted = promoted;
await categories.save();
return categories;
};
const deleteCategories = async (id) => {
const categories = await getCategoriesById(id);
if (!categories) return null;
await categories.deleteOne();
return categories;
};
module.exports = {createCategories, getCategoriesById, getCategories, updateCategories, deleteCategories }