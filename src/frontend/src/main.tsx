import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import {SWRConfig} from 'swr'

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
      <SWRConfig value={{fetcher: (url) => fetch(url).then((res) => res.json())}}>
        <App />
      </SWRConfig>
    </React.StrictMode>
)
