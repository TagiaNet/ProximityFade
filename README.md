# Proximity Fade

Hide players from each other based on proximity.

ProximityFade supports servers
running [Paper](https://papermc.io/software/paper) for Minecraft version `1.21.8`.

Report bugs by [creating](https://github.com/TagiaNet/ProximityFade/issues/new) an issue.

https://github.com/user-attachments/assets/0b617131-feba-4668-a97b-fc440f8c3798

## Configuration

### `config.yml`

```yaml
# Player will become partially invisible
nearbyDistanceHorizontal: 8.0
nearbyDistanceVertical: 8.0
# Player will become completely invisible
closeDistanceHorizontal: 4.0
closeDistanceVertical: 4.0
```

## Planned Features

If there is a planned feature which you need for your
server, [create an issue](https://github.com/TagiaNet/ProximityFade/issues/new) and I will give it a higher priority.

- Add configurable sprinting and potion particle effects
    - Currently, potion particles are always hidden when near and sprint particles are always visible
- Add option to disable close player collision
- Configurable nearby equipment retention and fill items
    - Currently, when players are near (partially invisible) they will retain their boots if they are wearing any,
      otherwise they will have leather boots displayed
    - I plan to allow you to customize which slots are retained and change the default item if the slots are empty
- Allow per-user and global toggles and value configuration
    - Provide commands and API access for customization
    - For example, players with the correct permission should be able to disable ProximityFade for themselves or
      customize values within defined performance limits
- Customizable nearby and close effects
  - Currently, players are only partially invisible (only boots shown) when near and completely invisible when close.
- Make encounter events cancellable
- Improve performance
    - This is low on the planned features list because the primary use-case envisioned for this plugin is on servers
      which already have a low performance overhead (i.e. parkour servers). There are a few obvious performance
      improvements I could make, but currently the benefit is outweighed by the costs (i.e. less-readable code,
      introducing some complexity which will make planned features more difficult to implement). If you are using this
      plugin and notice significant *measurable* performance issues create an issue and I will raise the priority
      of performance improvements.
