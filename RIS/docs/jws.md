# TKDE paper

## TODO

- [ ] Plots
    - [x] BTC large BGP queries
    - [x] LUBM large BGP queries
    - [x] BTC small BGP queries
    - [x] LUBM small BGP queries
    - [x] BTC multi-BGP queries
    - [ ] capacity of CBFs/unions in sorted order (high to low)
    - [x] query parsing and rewriting time
- [x] Coding
    - [x] reduce PV construction time
    - [x] use Virtuoso API for query refinement phase [Anas]
- [x] Experiments
    - [x] Index with Virtuoso
        - [x] BTC quads
        - [ ] ~~BTC triples~~
        - [ ] ~~LUBM triples~~
    - [x] Gather Virtuoso refinement time (RIQ+Virtuoso)
        - [x] LUBM triples, large queries
        - [x] BTC triples, large queries
        - [ ] ~~BTC quads, large queries~~
    - [x] Gather standalone Virtuoso on multi-BGP
    - [ ] ~~Change B10 and B11 to produce more results to slow down Virtuoso
          (replace URIs/literals with variables)~~
