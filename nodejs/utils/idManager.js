const Counter = require('../models/IdsCounter'); // Adjust path if needed

// Function to generate a new ID or reuse an existing one
async function generateId(counterId) {
    const counter = await Counter.findById(counterId);

    if (counter && counter.reusableIds.length > 0) {
        // Use a reusable ID
        const id = counter.reusableIds.shift();
        await counter.save(); // Save changes to the reusable pool
        return id;
    } else {
        // Increment sequence number and return new ID
        const updatedCounter = await Counter.findByIdAndUpdate(
            { _id: counterId },
            { $inc: { seq: 1 } },
            { new: true, upsert: true } // Create counter if not exists
        );
        return updatedCounter.seq;
    }
}


async function addReusableId(counterId, type) {//the type is for example (categorieId,movieId,userId)
    const counter = await Counter.findById(counterId);
    if (counter) {
        counter.reusableIds.push(type);
        await counter.save(); // Save changes to the reusable pool
    }
}

module.exports = {
    generateId,
    addReusableId
};