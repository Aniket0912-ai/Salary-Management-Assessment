# AI Usage

GitHub Copilot was used throughout development as an engineering acceleration tool.

## Uses

- Project scaffolding
- Boilerplate generation
- DTO and repository generation
- Test-case suggestions
- SQL/query review
- Angular component scaffolding
- Documentation assistance
- Code review and edge-case identification

## Engineering Review

AI-generated code was reviewed before being committed.

For business-critical functionality, including salary updates, salary history, audit creation, and analytics, tests were written and executed independently of the AI-generated implementation.

AI suggestions were rejected or modified when they introduced:
- unnecessary abstractions
- excessive framework complexity
- inefficient database access
- client-side processing that should occur on the server

## Example AI Workflow

Requirement
    ↓
AI-assisted design
    ↓
Human review
    ↓
Implementation
    ↓
AI-assisted code review
    ↓
Automated tests
    ↓
Manual verification
    ↓
Commit
