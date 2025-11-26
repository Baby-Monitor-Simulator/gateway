const WebSocket = require('ws');
import {buildData} from './StubDataGenerator.js';

// Create WebSocket connection
const ws = new WebSocket('ws://localhost:8080'); // Replace with your server URL

let intervalId;

// Connection opened
ws.addEventListener('open', (event) => {
    console.log('Connected to WebSocket server');
    let currentTimesteps = 0;
    // Send simulation update every 2 seconds
    intervalId = setInterval(() => {
        currentTimesteps = sendSimulationUpdate(currentTimesteps);
    }, 250);
});

// Listen for messages
ws.addEventListener('message', (event) => {
    try {
        const data = JSON.parse(event.data);
        // console.log('Received message:', data);

        switch (data.type) {
            case 'welcome':
                console.log('Welcome message:', data.message);
                break;
            
            case 'ping.pong':
                console.log('Pong received');
                break;
            
            case 'echo':
                console.log('Echo response:', data.data);
                break;
            
            case 'broadcast':
                console.log('Broadcast received:', data.payload);
                break;
            
            case 'simulation.update':
                console.log('Simulation update:', data.message);
                break;
            
            case 'error':
                console.error('Server error:', data.message);
                break;
            
            default:
                console.log('Unknown message type:', data.type);
        }
    } catch (error) {
        console.error('Error parsing message:', error);
    }
});

// Connection closed
ws.addEventListener('close', (event) => {
    console.log('Disconnected from WebSocket server');
    if (intervalId) {
        clearInterval(intervalId);
    }
});

// Connection error
ws.addEventListener('error', (error) => {
    console.error('WebSocket error:', error);
});

// Send a simulation update
function sendSimulationUpdate(currentTimesteps = 0) {
    if (ws.readyState === WebSocket.OPEN) {
        let data = buildData(currentTimesteps);
        updateData = JSON.stringify(data);
        currentTimesteps = data.payload.total_timesteps;
        ws.send(updateData);
        console.log('Sent simulation update:', updateData);
    }
    return currentTimesteps
}

// Handle process termination
process.on('SIGINT', () => {
    console.log('Closing client...');
    if (intervalId) {
        clearInterval(intervalId);
    }
    ws.close();
    process.exit(0);
});