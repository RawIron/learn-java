

class Sketches {
    player.earned(premium)
    player.finished(level)
    on(player).reward(xp)
    on(player).consume(energy)

    on(player).level.listenOn(xp)
    on(player).rewards.listenOn(inventory)
    on(player).quests.listenOn([inventory,xp])

    on(board).place(building).at(2,3)
    on(board).move(building).from(2,3).to(4,5)
    mover.run(move)

    make(transaction).from(building)
    transaction.give(xp)
    transaction.give(building).take(gold)
    transaction.give(building).to(player).take(gold).from(player)
    broker.run(transaction)
    on(player).run(transaction)

    ask.price(building)
    place.order(building)
    accept.order(building)
}


class EventEngine {
counters := store current state
sequencers := register at counters
actions := manipulate counters

    xp(counter)
    coins(counter)

    level(sequencer)
    quest(sequencer)
    achievement(sequencer)
}

