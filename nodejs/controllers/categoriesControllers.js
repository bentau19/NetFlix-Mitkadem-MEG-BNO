
const CategoriesService = require('../services/CategoriesService');
const ERROR_MESSAGES = require('../validation/errorMessages');
const getCategories = async (req, res) => {
    try {
        const result = await CategoriesService.getCategories()
        if (result) {
            res.status(200).json({ message: result});
        } else {
            res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST });
        }
    } catch (error) {
        if( ERROR_MESSAGES.BAD_REQUEST==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
    }
};
const createCategory = async (req, res) => {
    try {
        const result=await CategoriesService.createCategories(req.body.name,req.body.promoted);
        if (result) {
            res.status(201).json({ message: result});
        } else {
            res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST });
        }
    } catch (error) {
        if( ERROR_MESSAGES.BAD_REQUEST==error||ERROR_MESSAGES.Existing("category")==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
    }
    
};
const getCategorieById = async (req, res) => {
    try {
        const result = await CategoriesService.getCategoriesById(req.params.id);
        if (result) {
            res.status(200).json({ message: result});
        } else {
            res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST });
        }
    } catch (error) {
        if( ERROR_MESSAGES.BAD_REQUEST==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
    }
};
const updateCategory = async (req, res) => {
    try {
        const result =await CategoriesService.updateCategories(req.params.id,req.body.name,req.body.promoted);
        if (result) {
            res.status(204).json({ message: result});
        } else {
            res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST });
        }
    } catch (error) {
        if( ERROR_MESSAGES.BAD_REQUEST==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
    }
};
const deleteCategory = async (req, res) => {
    try {
        const result =await CategoriesService.deleteCategories(req.params.id);
        if (result) {
            res.status(204).json({ message: result});
        } else {
            res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST });
        }
    } catch (error) {
        if( ERROR_MESSAGES.BAD_REQUEST==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
    }
};

const getQueryCat = async (req, res) => {
    try {
        const query = req.params.query; // Get the query parameter from the URL

        const result = await CategoriesService.getQueryCat(
            query
        );
        if (result) {
            // Assuming createUser returns a truthy value on success
            res.status(200).json(result);
        } else {
            res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST});
        }
    } catch (error) {
        if( ERROR_MESSAGES.BAD_REQUEST==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
    }
};
module.exports = {getCategories, createCategory, getCategorieById,updateCategory,deleteCategory,getQueryCat };