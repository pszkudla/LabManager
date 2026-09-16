import styled from "styled-components";
import {createPortal} from "react-dom";
import {useEffect} from "react";

const PostModal = styled.div`
    position: fixed;
    top: 20px;
    left: 90%;
    transform: translateX(-50%);

    width: 200px;
    min-height: 50px;
    padding: 20px;

    color: black;
    font-size: 14px;
    border-radius: 8px;
    z-index: 1000;
    
    text-align: center;
    align-content: center;
    border: 1px solid black;
    
`

function PostStatusModal({status, message, lifeTime=5000, onClose}) {
    useEffect(() => {
            const timeoutId = setTimeout(() => {
                onClose();
            }, lifeTime);

            return () => {
                clearTimeout(timeoutId);
            };
        }
    )
    const backgroundColor = status >= 200 && status < 300 ? "#40d140" : "#ee3a51";

    return createPortal(
        <PostModal style={{ backgroundColor }}>{message}</PostModal>
        , document.body
    );

    // let backgroundColor = "red";
    // if (status == 200) {
    //     backgroundColor = "green";
    // }
    // return createPortal(<PostModal style={[backgroundColor]}>{message}</PostModal>, document.body);
}
export default PostStatusModal;