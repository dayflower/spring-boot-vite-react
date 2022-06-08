import useSWR, {mutate} from 'swr';

type GetRandomResponse = {
  value: string;
};

function App() {
  const {data, error} = useSWR<GetRandomResponse>('/api/random', {
    fallbackData: {value: 'INITIAL'},
    revalidateIfStale: false,
    revalidateOnMount: false,
    revalidateOnFocus: false,
  });

  const onClickButton = () => {
    mutate('/api/random');
  };

  return (
      <div className="App">
        <button type="button" onClick={onClickButton}>
          value is:
          {!error && data && data.value}
          {error && 'ERROR'}
        </button>
      </div>
  )
}

export default App
