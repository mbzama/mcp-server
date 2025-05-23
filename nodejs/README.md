Running directly with node module using Claude Desktop

1) Install npm dependency:
  npm install -g @modelcontextprotocol/server-postgres

2) Run mcp server using this command:
  npx -y @modelcontextprotocol/server-postgres postgresql://username:password@localhost:5432/auction_db

3) Open Claude Desktop and go to
   File --> Settings

4) Open or create the claude_desktop_config.json file

5) Add configuration for postgres database
   {
      "mcpServers": {
        "postgres": {
          "command": "npx",
          "args": [
            "-y",
            "@modelcontextprotocol/server-postgres",
            "postgresql://username:password@localhost:5435/action_db"
          ]
        }
      }
    }

6) Exit and Re-launch Claude Desktop 





