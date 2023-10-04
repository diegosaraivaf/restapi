// Layout.js
import React from 'react';
import { Menu } from './Menu';

function Layout({ children }) {
  return (
    <>
      <div className='flex flex-wrap'>
        <header id='head-layout'>Header Content</header>
        <aside id="aside-layout"><Menu/></aside>
        <main id="main-layout">{children}</main>
        <footer id='footer-layout'>Fotter</footer>
      </div>
    </>
  );
}

export default Layout;