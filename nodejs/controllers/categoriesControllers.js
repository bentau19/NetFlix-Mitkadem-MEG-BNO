
const CategoriesService = require('../services/CategoriesService');

const getCategories = async (req, res) => {
    res.json(await CategoriesService.getCategories());
};
const createCategory = async (req, res) => {
    res.json(await CategoriesService.createCategories(req.body.name,req.body.promoted));
};
const getCategorieById = async (req, res) => {
    const article = await CategoriesService.getCategoriesById(req.params.id);
        if (!article) {
        return res.status(404).json({ errors: ['Categories not found'] });
        }
        res.json(article);
};
const updateCategory = async (req, res) => {
    res.json(await CategoriesService.updateCategories(req.params.id,req.body.name,req.body.promoted));
};
const deleteCategory = async (req, res) => {
    res.json(await CategoriesService.deleteCategories(req.params.id));
};
module.exports = {getCategories, createCategory, getCategorieById,updateCategory,deleteCategory };