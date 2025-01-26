const counter = require('../utils/idManager');
const Categories = require('../models/categories');
const Movie = require('../models/movies');
const ERROR_MESSAGES = require('../validation/errorMessages');

const createCategories = async (name, promoted) => {
if(!name)throw ERROR_MESSAGES.BAD_REQUEST;
const test = await Categories.findOne({ name:name });
if (test) throw ERROR_MESSAGES.Existing("category");
const categories = new Categories({ name : name});
if (promoted) categories.promoted = promoted;
return await categories.save();
};

const getCategoriesById = async (id) => {
    if(!id)throw ERROR_MESSAGES.BAD_REQUEST;
    return await Categories.findById(id); };
const getCategories = async () => { return await Categories.find({}); };
const updateCategories = async (id, name,promoted) => {
if(!id)throw ERROR_MESSAGES.BAD_REQUEST;
const categories = await getCategoriesById(id);
if (!categories) return null;
if(name)categories.name = name;
if(promoted==false||promoted)categories.promoted = promoted;
await categories.save();
return categories;
};
const deleteCategories = async (id) => {
if(!id)throw ERROR_MESSAGES.BAD_REQUEST;
const categories = await getCategoriesById(id);
if (!categories) throw ERROR_MESSAGES.BAD_REQUEST;
await  Movie.updateMany(
    { categories: id }, // Find movies where the category ID exists in the 'categories' array
    { $pull: { categories: id } } // Remove the category ID from the 'categories' array
);
await categories.deleteOne();
counter.addReusableId("categorieId", id);
return categories;
};

const getQueryCat = async (query) => {
  if(!query)
    return await Categories.find({}); 

  const conditions = {
    $or: [
      { name: { $regex: query, $options: 'i' } } // Case-insensitive search on logline
    ]
  };
  const testQuery = await Categories.find(conditions);

  return testQuery;
};

module.exports = {createCategories, getCategoriesById, getCategories, updateCategories, deleteCategories,getQueryCat }