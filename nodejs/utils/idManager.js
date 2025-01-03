const Counter = require('../models/IdsCounter'); // Adjust path if needed

// Function to generate a new ID or reuse an existing one
async function generateId(counterId) {
    if (!counterId) {
        throw new Error("Counter ID must be provided");
    }

    const counter = await Counter.findById(counterId);

    if (counter && counter.reusableIds.length > 0) {
        // Use a reusable ID
        const id = counter.reusableIds.shift();
        await counter.save(); // Save changes to the reusable pool
        return id;
    } else {
        // Increment sequence number and return new ID
        const updatedCounter = await Counter.findByIdAndUpdate(
            counterId, // Ensure counterId is properly set
            { $inc: { seq: 1 } },
            { new: true, upsert: true } // Create counter if not exists
        );
        return updatedCounter.seq;
    }
}
async function addReusableId(counterId, reusableId) {
    if (!counterId) {
        throw new Error("Counter ID must be provided");
    }

    const counter = await Counter.findById(counterId);
    if (counter) {
        counter.reusableIds.push(reusableId);
        await counter.save(); // Save changes to the reusable pool
    } else {
        // Create a new counter if it doesn't exist
        await Counter.create({ _id: counterId, reusableIds: [reusableId] });
    }
}

module.exports = {
    generateId,
    addReusableId
};