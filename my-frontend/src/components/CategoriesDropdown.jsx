import React, { useEffect, useState } from "react";
import { getCategory } from "./httpUtils";

const CategoriesDropdown = ({setSelectedOption ,selectedOption}) => {
  const [categories, setCategories] = useState([]); // State for fetched categories

  useEffect(() => {
    // Fetch categories asynchronously
    const fetchCategories = async () => {
      try {
        const data = await getCategory(""); // Assuming getCategory returns a promise
        setCategories(data); // Update state with fetched categories
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    };

    fetchCategories();
  }, []); // Run once on component mount

  const handleOptionSelect = (event) => {
    setSelectedOption(event.target.value); // Update the selected option state
  };

  return (
    <form id="form1">
      <select
        className="categoriesDropdown"
        value={selectedOption} // Bind the value to the state
        onChange={handleOptionSelect} // Update state on selection change
      >
        <option value="">Main</option>
        <option value="all">All Categories</option>
        {categories.map((category) => (
          <option key={category.id} value={category.name}>
            {category.name}
          </option>
        ))}
      </select>
    </form>
  );
};

export default CategoriesDropdown;
