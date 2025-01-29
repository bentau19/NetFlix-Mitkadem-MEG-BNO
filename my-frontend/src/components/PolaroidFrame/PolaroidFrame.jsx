import React from 'react';
import './PolaroidFrame.css';

const PolaroidFrame = ({ imageSrc, altText, caption }) => {
  return (
    <div className="polaroid-frame">
      <img src={imageSrc} alt={altText} className="photo" />
      {caption && <div className="caption">{caption}</div>}
    </div>
  );
};

export default PolaroidFrame;
