
export function buildData (total_timesteps) {
  //setting up needed arrays
  let timeSteps = Math.round(Math.random() * 10) + 3;
  total_timesteps += timeSteps;
  let motherOx_data = [];
  let motherToco_data= []; 
  let fetus_data= [[]];

  //generating fake data
  for(let i = 0; i < timeSteps; i++){
    let fetusHartRate = Math.random() * 10 + 140;
    let motherOx = Math.random() * 10 + 90;
    let motherToco = Math.random() * 10 + 10;

    fetus_data[0] = fetus_data[0].concat(fetusHartRate)
    motherOx_data = motherOx_data.concat(motherOx)
    motherToco_data = motherToco_data.concat(motherToco)
  }

  let graphData = {
    type: "simulation.update",
    version: "1.0",
    payload: {
      total_timesteps: total_timesteps,
      timesteps: timeSteps,
      fetus_count: 1,
      maternal_data: {
        toco: motherToco_data,
        maternal_oxygen_saturation: motherOx_data,
      },
      fetus_data: fetus_data
    },
  }

  return graphData;
}