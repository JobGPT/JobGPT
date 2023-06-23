import React from 'react';
import { useStore } from '../store.js';

export default function GptChat() {
  const company_info = useStore((store) => store.company_info);

  const renderObject = (obj) => {
    return Object.entries(obj).map(([key, value]) => {
      if (typeof value === 'object' && value !== null) {
        return (
          <div key={key}>
            <span>{key}: </span>
            {renderObject(value)}
          </div>
        );
      } else {
        return (
          <div key={key}>
            <span>{key}: </span>
            <span>{value}</span>
          </div>
        );
      }
    });
  };

  return <>{renderObject(company_info.summery.company_overview)}</>;
}
