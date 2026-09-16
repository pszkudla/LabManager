import { useState } from 'react'
import Header from './mainComponents/Header.jsx'
import './App.css'
import styled from 'styled-components'
import LeftMenu from "./mainComponents/LeftMenu.jsx";
import AddLaboratory from './pages/AddLaborary.jsx'
import AddShelf from "./pages/AddShelf.jsx";
import AddCabinet from "./pages/AddCabinet.jsx";

const ContentContainer = styled.div`
    display: flex;
    flex-direction: row;
    width: 100%;
    height: 1000px;
`

const MainContainer = styled.div`
    width: calc(70% - 2px);
    border-right: 1px solid black;
    border-left: 1px solid black;
    padding: 20px;
    text-align: center;
`

const RightMenu = styled.div`
    width: 15%;
`


function App() {

    const [contentOption, setContentOption] = useState("addLab");

    let mainContent;
    switch (contentOption) {
        case "addLab":
            mainContent = <AddLaboratory/>
            break;
        case "addShelf":
            mainContent = <AddShelf/>
            break;
        case "addCabinet":
            mainContent = <AddCabinet/>
            break;

    }

  return (
    <>
        <Header/>
        <ContentContainer>
            <LeftMenu setContentFunction={setContentOption}/>
            <MainContainer>
                {mainContent}
            </MainContainer>

            <RightMenu/>
        </ContentContainer>
    </>
  )
}

export default App
