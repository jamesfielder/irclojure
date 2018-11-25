const irc = require('slate-irc');
const net = require('net');
const faker = require('faker');
var sleep = require('sleep');

const bots = 20;

[...Array(bots).keys()].map(c => {
  /** The ircd does not like it when 20 clients try to connect at the same time from the same ip*/
  sleep.msleep(400)

  const stream = net.connect({
    port: 6667,
    host: '127.0.0.1'
  })

  const client = irc(stream)
  client.nick(faker.internet.userName())
  client.user("user")
  client.join('#test')

  setInterval((client) => randomData(client), 1000, client);

  return client
})

const randomData = (client) => {
  try {
    client.send('#test', faker.lorem.sentence())
  }
  catch (e) {
    console.log("Error: " + e)
  }
}