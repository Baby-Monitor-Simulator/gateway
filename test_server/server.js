const WebSocket = require('ws');
const port = process.env.PORT || 8080;

const wss = new WebSocket.Server({ port });

console.log(`WebSocket server running on port ${port}`);

//wss.on('connection', (ws) => {
//  console.log('Client connected');

//  ws.send('Welcome! Connected to backend via Kong');

//  ws.on('message', (message) => {
//    console.log('Received:', message.toString());
//    ws.send(`Echo: ${message}`);
//  });

//  ws.on('close', () => {
//    console.log('Client disconnected');
//  });
//});

wss.on('connection', (ws) => {
    console.log('Client connected');

    // Send welcome message as JSON
    ws.send(JSON.stringify({
        type: 'welcome',
        message: 'welcome',
        timestamp: new Date().toISOString()
    }));

    ws.on('message', (message) => {
        try {
            const data = JSON.parse(message.toString());
            console.log('Received:', data);

            switch (data.type) {
                // check connectie
                case 'ping.ping':
                    ws.send(JSON.stringify({
                        type: 'ping.pong',
                        timestamp: new Date().toISOString()
                    }));
                    break;

                // echo debug
                case 'echo':
                    ws.send(JSON.stringify({
                        type: 'echo',
                        data: data,
                        timestamp: new Date().toISOString()
                    }));
                    break;

                // Broadcast to ALL connected clients for testin purpusus
                case 'broadcast':
                    wss.clients.forEach((client) => {
                        if (client.readyState === WebSocket.OPEN) {
                            client.send(JSON.stringify({
                                type: 'broadcast',
                                version: data.version,
                                payload: data.payload,
                                timestamp: new Date().toISOString()
                            }));
                        }
                    });
                    break;

                // updates all andere clients wanneer een update binnen komt
                case 'simulation.update':
                    wss.clients.forEach((client) => {
                        if (client !== ws && client.readyState === WebSocket.OPEN) {
                            client.send(JSON.stringify({
                                type: 'simulation.update',
                                message: data,
                                timestamp: new Date().toISOString()
                            }));
                        }
                    });
                    break;

                default:
                    ws.send(JSON.stringify({
                        type: 'error',
                        message: `Unknown message type: ${data.type}`,
                        timestamp: new Date().toISOString()
                    }));
            }
        }
        catch (error) {
            // Handle invalid JSON
            console.error('Invalid JSON received:', error);
            ws.send(JSON.stringify({
                type: 'error',
                message: 'Invalid JSON format',
                timestamp: new Date().toISOString()
            }));
        }
    });

    ws.on('close', () => {
        console.log('Client disconnected');
    });
});