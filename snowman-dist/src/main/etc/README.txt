TARGETS

clean           Removes all files created the run-* targets from the temporary
                directory.

run-client      Runs a client process.

run-sim		Runs the client simulator.

run-single      Runs a single node application server.

run-server      Runs the application server in multi-node mode.

run-core        Runs the core services in multi-node mode.

run-editor	Runs the world editor

PROPERTIES

property name:  server.host
default value:  localhost
description:    Host name of application server node. Used in the run-client
                target and should be set to the name of the host where either
                the run-single or run-server targets were invoked.

property name:  server.port
default value:  3000
description:    Host port of application server. Used in the run-client,
                run-server, and run-single targets.

property name:  core.host
default value:  localhost
description:    Host name of core server node. Used in run-server target and
                should be set to the name of the host where run-core was invoked.

property name:  use.je
default value:  <undefined>
description:    If defined use Berkley DB Java Edition.

property name:  tmp.dir
default value:  tmp
description:    Directory name to use for temporary property files and
                database files.

property name:  retain.datastore
default value:  <undefined>
description:    If defined, the database files will not be deleted each time
                run-server or run-core is invoked.

property name:	maxClients
default value:	1000
description:	Maximum number of clients that a client simulator VM can startup

property name:	newClientDelay
default value:	175
description:	Delay in milliseconds between adding clients to the server

property name:	move.delay
default value:	5000
description:	Minimum time required in ms between simulated client moves

property name:	numPlayersPerGame
default value:	4
description:	Number of players per game on the server side

EXAMPLES

To run the server and a client process on the same node:

    ant run-single
    ant run-client

To run a multi-node server and a client on three nodes using BDB Java edition:

    on node1:

    ant -Duse.je= run-core

    on node2

    ant -Dcore.host=node1 run-server

    on node3

    ant -Dserver.host=node2 run-client

To run the client simulator connecting to localhost

    ant run-sim

To run the world editor

    ant run-editor

