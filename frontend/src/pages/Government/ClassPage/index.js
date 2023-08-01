// import axios from '../../Common/axios';  // 일단 axios도 만들어 둠
// import React from 'react'
// import { useEffect, useState, useCallback } from 'react';
// import StudyClassModal from '../components/StudyClassModal';
// import { styled } from 'styled-components';


// const StudyClassPage = () => {
//   const [studyClasses, setStudyClasses] = useState([])  // 반은 여러개니까 배열로 받기위해서 State안에 [] 배열로 적어줌
//   const [modalOpen, setModalOpen] = useState(false); // 모달 오픈을 위해 열리기 전에는 false
//   const [studyClassSelected, setStudyClassSelected] = useState({})
//   const [absStudentOpne, setAbsStudentOpne] = useState(false);
//   const [studentSelected, setStudentClassSelected] = useState({})

//   // async 비동기로 useEffect에서 사용하기위해 !먼저! 만들어 준다.
//   const fetchStudyClassData = useCallback(async () => {  // useCallback을 바뀌는 걸 괄호닫은 [] 대괄호 배열에 바뀌는 변수를 넣어준다.
//     const response = await axios.get(fetchUrl);  // 그런데 Axios 단축으로 불러오면 axios폴더에서 axios를 가져오니까 위에서 고쳐줘야함
//     // console.log("response", response);
//     setStudyClasses(response.data.results);
//   }, [fetchUrl]);

//   useEffect(() => {
//     fetchStudyClassData();  // fetch
//   }, [fetchStudyClassData]) // useCallback과 마찬가지로 fetchMovieData가 변하는걸 넣어준다.
//   // 컴포넌트가 실행되면 함수도 새로 생성된다. 하지만 함수를 굳이 재생성 할 필요는 없기 때문에 useCallback을 사용한다.

//   const handleClick = (studyclass) => { // studyclass state 넘겨줘야하니까 넣어주고
//     setModalOpen(true);
//     setStudyClassSelected(studyclass);
//   }

//   return (
//     <div>
//       <StudyLst>
//         {studyClasses.map((studyclass) => (
//           <StudyIndvd
//           // 클릭했을 때 handleClick이라는 함수를 불러오겠다.
//           onClick={() => handleClick(studyclass)} > 
//             {studyclass.id}
//             {studyclass.class_name}
//             {studyclass.lecture_time}
//           </StudyIndvd>
//         ))}
//       </StudyLst>
//       {
//         modalOpen && 
//         <StudyClassModal // StudyClassModal 컴포넌트 가져오고
//           {...studyClassSelected} // 전개 연산자 ...
//           setModalOpen={setModalOpen} // props로 setModalOpen을 내려준다. 
//           // 그러고 나면 StudyClassModal의 index.js에 가서 함수에다가 setModalOpen(바꿔줄값) 적어줌 / 바꿔줄 값은 false이다.
//         />
//       }
//     </div>
//   )
// }

// export default StudyClassPage

// const StudyLst = styled.div`

// `

// const StudyIndvd = styled.div`
// `