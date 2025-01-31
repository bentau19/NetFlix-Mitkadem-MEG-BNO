
const CategoriesService = require('../services/CategoriesService');
const ERROR_MESSAGES = require('../validation/errorMessages');
const VALIDITY_FUNC = require('../validation/validityFunc');

const getCategories = async (req, res) => {
    try {
        const result = await CategoriesService.getCategories()
        VALIDITY_FUNC.validProgram(result,200,res)
    } catch (error) {
        VALIDITY_FUNC.catchAction(error,res);
    }
};
const createCategory = async (req, res) => {
    try {
        VALIDITY_FUNC.adminExistingValidity(req.headers['token'])
        const result=await CategoriesService.createCategories(req.body.name,req.body.promoted);
        VALIDITY_FUNC.validProgram(result,201,res)
    } catch (error) {
        if(ERROR_MESSAGES.Existing("category")==error)
            res.status(400).json({ message: error});
        else VALIDITY_FUNC.catchAction(error,res);
    }
    
};
const getCategorieById = async (req, res) => {
    try {
        const result = await CategoriesService.getCategoriesById(req.params.id);
        VALIDITY_FUNC.validProgram(result,200,res)
    } catch (error) {
        VALIDITY_FUNC.catchAction(error,res);
    }
};
const updateCategory = async (req, res) => {
    try {
        VALIDITY_FUNC.adminExistingValidity(req.headers['token'])
        const result =await CategoriesService.updateCategories(req.params.id,req.body.name,req.body.promoted);
        VALIDITY_FUNC.validProgram(result,204,res)
    } catch (error) {
        VALIDITY_FUNC.catchAction(error,res);
    }
};
const deleteCategory = async (req, res) => {
    try {
        VALIDITY_FUNC.adminExistingValidity(req.headers['token'])
        const result =await CategoriesService.deleteCategories(req.params.id);
        VALIDITY_FUNC.validProgram(result,204,res)
    } catch (error) {
        VALIDITY_FUNC.catchAction(error,res);
    }
};

const getQueryCat = async (req, res) => {
    try {
        const query = req.params.query; // Get the query parameter from the URL

        const result = await CategoriesService.getQueryCat(
            query
        );
        VALIDITY_FUNC.validProgram(result,200,res)
    } catch (error) {
        VALIDITY_FUNC.catchAction(error,res);
    }
};
module.exports = {getCategories, createCategory, getCategorieById,updateCategory,deleteCategory,getQueryCat };